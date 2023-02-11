package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.TurretConstants;

public class TurretSubsystem extends SubsystemBase {
    private final WPI_TalonFX rotate = new WPI_TalonFX(TurretConstants.kTurretTalonFX);
    private SlewRateLimiter rateLimiter = new SlewRateLimiter(0.75);

    public TurretSubsystem() {
        rotate.setNeutralMode(NeutralMode.Brake);
        rateLimiter.reset(0);
    }

    public void set(double value) {
        if (value == 0) {
            rateLimiter.reset(0);
        }
        rotate.set(TalonFXControlMode.PercentOutput,
                value == 0 ? value : rateLimiter.calculate(value) * TurretConstants.kMaxOutput);
    }

    public Command moveCmd(DoubleSupplier input) {
        return this.runEnd(() -> this.set(input.getAsDouble()), () -> this.set(0));
    }
}
