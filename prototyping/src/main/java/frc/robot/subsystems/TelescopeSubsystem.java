package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.TelescopeConstants;

public class TelescopeSubsystem extends SubsystemBase {
    private final WPI_TalonSRX extensionTalon = new WPI_TalonSRX(TelescopeConstants.kExtensionTalonSRX);
    private final SlewRateLimiter rateLimiter = new SlewRateLimiter(0.5);

    public TelescopeSubsystem() {
        extensionTalon.setNeutralMode(NeutralMode.Brake);
        rateLimiter.reset(0);
    }

    public void set(double value) {
        if (value == 0) {
            rateLimiter.reset(0);
        }
        extensionTalon.set(TalonSRXControlMode.PercentOutput, value == 0 ? value : rateLimiter.calculate(value));
    }

    public Command moveCmd(DoubleSupplier input) {
        return this.startEnd(() -> this.set(input.getAsDouble()), () -> this.set(0));
    }
}
