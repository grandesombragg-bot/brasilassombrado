package br.assombrado;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.SpiderNavigation;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Corpo Seco: vida de Ender Dragon, apaga lampiões, sobe cercas, ataca as paredes com sorte variável,
 * invoca Enraizados e amaldiçoa quem acerta. Não aparece sozinho: o gatilho da capela o chama.
 */
public class CorpoSecoEntity extends HostileEntity {
    private static final TrackedData<Integer> EMERGE = DataTracker.registerData(CorpoSecoEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> ESCALANDO = DataTracker.registerData(CorpoSecoEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final int EMERGE_TOTAL = 90;
    public static final int MORTE_TOTAL = 90;

    private final ServerBossBar bossBar = new ServerBossBar(Text.translatable("entity.brasil_assombrado.corpo_seco"),
            BossBar.Color.PURPLE, BossBar.Style.NOTCHED_10);
    private int investidaCooldown = 100;
    private int invasaoTicks = 0;
    private int invocarCooldown = 220;
    private int totalInvocados = 0;
    private int raizesCooldown = 320;
    private final List<UUID> capangas = new ArrayList<>();

    public CorpoSecoEntity(EntityType<? extends HostileEntity> tipo, World mundo) {
        super(tipo, mundo);
        this.experiencePoints = 500;
        this.setPersistent();
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 200.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.30)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 12.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_ARMOR, 6.0);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(EMERGE, 0);
        builder.add(ESCALANDO, false);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.1, false));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.6));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 16.0f));
        this.goalSelector.add(7, new LookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Override
    protected EntityNavigation createNavigation(World mundo) {
        return new SpiderNavigation(this, mundo);
    }

    @Override
    public boolean isClimbing() {
        return this.dataTracker.get(ESCALANDO);
    }

    public int getEmerge() {
        return this.dataTracker.get(EMERGE);
    }

    public void iniciarEmersao() {
        this.dataTracker.set(EMERGE, EMERGE_TOTAL);
    }

    @Override
    protected boolean isImmobile() {
        return super.isImmobile() || getEmerge() > 0;
    }

    @Override
    public boolean damage(DamageSource fonte, float quantidade) {
        if (getEmerge() > 0 && !fonte.isOf(DamageTypes.GENERIC_KILL)) return false;
        return super.damage(fonte, quantidade);
    }

    @Override
    public boolean canImmediatelyDespawn(double distancia) {
        return false;
    }

    @Override
    public void onStartedTrackingBy(ServerPlayerEntity jogador) {
        super.onStartedTrackingBy(jogador);
        bossBar.addPlayer(jogador);
    }

    @Override
    public void onStoppedTrackingBy(ServerPlayerEntity jogador) {
        super.onStoppedTrackingBy(jogador);
        bossBar.removePlayer(jogador);
    }

    // ------------------------------------------------------------------ comportamento
    @Override
    public void tick() {
        super.tick();
        World mundo = getWorld();
        if (mundo.isClient) return;
        ServerWorld sw = (ServerWorld) mundo;
        bossBar.setPercent(getHealth() / getMaxHealth());

        int e = getEmerge();
        if (e > 0) {
            this.dataTracker.set(EMERGE, e - 1);
            if (e == EMERGE_TOTAL) {
                playSound(ModSounds.CS_GRITO, 3.0f, 0.6f);
                apagarLampioes(24);
            }
            if (e % 5 == 0) {
                sw.spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.COARSE_DIRT.getDefaultState()),
                        getX(), getY() + 0.1, getZ(), 14, 0.6, 0.1, 0.6, 0.1);
            }
            return;
        }
        if (isDead()) return;

        this.dataTracker.set(ESCALANDO, this.horizontalCollision && podeEscalar());
        if (age % 10 == 0) apagarLampioes(10);
        if (age % 4 == 0) contagiarMobs(sw);
        if (invasaoTicks > 0) invasaoTicks--;

        LivingEntity alvo = getTarget();
        if (alvo == null || !alvo.isAlive()) return;
        double dist2 = squaredDistanceTo(alvo);

        if (investidaCooldown > 0) {
            investidaCooldown--;
        } else if (dist2 < 22 * 22 && dist2 > 6.0 && (this.horizontalCollision || getNavigation().isIdle())) {
            investida(sw, alvo);
            investidaCooldown = 70 + random.nextInt(60);
        }
        if (invocarCooldown > 0) {
            invocarCooldown--;
        } else {
            invocar(sw);
            invocarCooldown = 500;
        }
        if (raizesCooldown > 0) {
            raizesCooldown--;
        } else if (dist2 < 100) {
            raizes(sw, alvo);
            raizesCooldown = 300 + random.nextInt(200);
        }
    }

    private boolean podeEscalar() {
        LivingEntity alvo = getTarget();
        if (alvo == null) return false;
        Direction d = Direction.getFacing(alvo.getX() - getX(), 0, alvo.getZ() - getZ());
        BlockPos frente = getBlockPos().offset(d);
        for (int dy = 0; dy <= 1; dy++) {
            net.minecraft.block.Block b = getWorld().getBlockState(frente.up(dy)).getBlock();
            if ((b instanceof GlassWallBlock || b instanceof MuroLisoBlock) && invasaoTicks <= 0) return false;
        }
        return true;
    }

    /** Sorte: 30% nada, 20% só ruim, 20% bastante ruim, 30% muito ruim. */
    private void investida(ServerWorld sw, LivingEntity alvo) {
        int r = random.nextInt(100);
        int nivel = r < 30 ? 3 : (r < 50 ? 2 : (r < 70 ? 1 : 0));
        Direction d = Direction.getFacing(alvo.getX() - getX(), 0, alvo.getZ() - getZ());
        playSound(ModSounds.CS_GRITO, 2.5f, 0.7f + random.nextFloat() * 0.4f);
        apagarLampioes(14);
        sw.spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.DARK_OAK_LOG.getDefaultState()),
                getX(), getY() + 1.0, getZ(), 18, 0.8, 0.8, 0.8, 0.05);
        if (nivel == 0) return;
        if (!sw.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) return;
        if (nivel == 3) invasaoTicks = 300;
        float dureza = nivel == 3 ? 10.0f : (nivel == 2 ? 3.0f : 0.6f);
        float chance = nivel == 3 ? 1.0f : (nivel == 2 ? 0.55f : 1.0f);
        int n = Destruicao.quebrarFrente(sw, this, d, dureza, chance, nivel == 3);
        if (n > 0) playSound(ModSounds.CS_QUEBRAR, 2.5f, 0.8f);
    }

    private void apagarLampioes(int raio) {
        World mundo = getWorld();
        BlockPos c = getBlockPos();
        for (BlockPos p : BlockPos.iterate(c.add(-raio, -4, -raio), c.add(raio, 5, raio))) {
            BlockState s = mundo.getBlockState(p);
            if (s.getBlock() instanceof LampiaoBlock && s.get(LampiaoBlock.ACESO)) {
                mundo.setBlockState(p, s.with(LampiaoBlock.ACESO, false), 3);
                mundo.playSound(null, p, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.6f, 0.8f);
            }
        }
    }

    private void contagiarMobs(ServerWorld sw) {
        Box caixa = getBoundingBox().expand(24.0);
        for (HostileEntity m : sw.getEntitiesByClass(HostileEntity.class, caixa, e -> e != this)) {
            if (m.getTarget() == null || !m.horizontalCollision) continue;
            Vec3d v = m.getVelocity();
            m.setVelocity(v.x, Math.max(v.y, 0.3), v.z);
            m.velocityModified = true;
            m.fallDistance = 0;
            if (random.nextInt(30) == 0 && sw.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                Destruicao.quebrarFrente(sw, m, Direction.fromRotation(m.getYaw()), 2.0f, 0.35f, false);
            }
        }
    }

    private void invocar(ServerWorld sw) {
        capangas.removeIf(id -> {
            Entity en = sw.getEntity(id);
            return en == null || !en.isAlive();
        });
        if (capangas.size() >= 4 || totalInvocados >= 12) return;
        int n = Math.min(2, 4 - capangas.size());
        for (int i = 0; i < n; i++) {
            EnraizadoEntity c = ModEntities.ENRAIZADO.create(sw);
            if (c == null) continue;
            BlockPos p = getBlockPos().add(random.nextInt(7) - 3, 0, random.nextInt(7) - 3);
            c.refreshPositionAndAngles(p.getX() + 0.5, p.getY(), p.getZ() + 0.5, random.nextFloat() * 360f, 0f);
            if (!sw.isSpaceEmpty(c)) {
                c.discard();
                continue;
            }
            c.setTarget(getTarget());
            sw.spawnEntity(c);
            capangas.add(c.getUuid());
            totalInvocados++;
            sw.spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.COARSE_DIRT.getDefaultState()),
                    c.getX(), c.getY() + 0.2, c.getZ(), 12, 0.4, 0.1, 0.4, 0.1);
        }
        playSound(ModSounds.CS_INVOCAR, 2.5f, 0.9f);
    }

    private void raizes(ServerWorld sw, LivingEntity alvo) {
        alvo.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 3));
        sw.spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.DARK_OAK_LOG.getDefaultState()),
                alvo.getX(), alvo.getY() + 0.1, alvo.getZ(), 24, 0.4, 0.1, 0.4, 0.1);
        playSound(ModSounds.CS_QUEBRAR, 1.5f, 1.2f);
    }

    @Override
    public boolean tryAttack(Entity alvo) {
        boolean acertou = super.tryAttack(alvo);
        if (acertou && alvo instanceof LivingEntity vivo) {
            vivo.addStatusEffect(new StatusEffectInstance(ModEffects.MALDICAO_SECA, 600, 0));
        }
        return acertou;
    }

    // ------------------------------------------------------------------ morte
    @Override
    public void onDeath(DamageSource fonte) {
        super.onDeath(fonte);
        if (getWorld() instanceof ServerWorld sw) {
            for (UUID id : capangas) {
                Entity e = sw.getEntity(id);
                if (e != null) e.kill();
            }
        }
    }

    @Override
    protected void updatePostDeath() {
        this.deathTime++;
        World mundo = getWorld();
        if (!mundo.isClient && this.deathTime >= MORTE_TOTAL && !this.isRemoved()) {
            ExperienceOrbEntity.spawn((ServerWorld) mundo, getPos(), 500);
            mundo.sendEntityStatus(this, EntityStatuses.ADD_DEATH_PARTICLES);
            this.remove(Entity.RemovalReason.KILLED);
        }
    }

    // ------------------------------------------------------------------ sons
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.CS_AMBIENTE;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource fonte) {
        return ModSounds.CS_FERIDO;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.CS_MORTE;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState estado) {
        playSound(ModSounds.CS_PASSO, 0.8f, 0.8f + random.nextFloat() * 0.4f);
    }

    @Override
    protected float getSoundVolume() {
        return 1.8f;
    }
}
