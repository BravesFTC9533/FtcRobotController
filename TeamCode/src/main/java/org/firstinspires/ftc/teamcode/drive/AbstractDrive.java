package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.common.FtcGamePad;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.drive.gyroManagers.AbstractGyroDriveManager;
import org.firstinspires.ftc.teamcode.drive.gyroManagers.MecanumGyroDriveManager;
import org.firstinspires.ftc.teamcode.drive.vuforiaManagers.MecanumVuforiaDriveManager;

/**
 * Created by 9533 on 11/15/2020.
 */

public abstract class AbstractDrive {

    // Required Protected Fields
    protected final Robot robot;
    protected final LinearOpMode opMode;

    // Drive Managers
    public MecanumVuforiaDriveManager vuforiaDriveManager = null;
    public MecanumGyroDriveManager gyroDriveManager = null;

    public AbstractDrive(LinearOpMode opMode, Robot robot) {
        this.robot = robot;
        this.opMode = opMode;
    }

    enum TurnDirection {
        CLOCKWISE,
        COUNTER_CLOCKWISE
    }

    public abstract boolean getIsReverse();
    public abstract void setIsReverse(boolean value);
    public abstract void handle(FtcGamePad driverGamepad);

    public abstract void drive(double ly, double lx, double rx);
    public abstract void drive(double left, double right);

    public abstract void gamePadButtonEvent(FtcGamePad gamepad, int button, boolean pressed);

    public abstract void driveByEncoderTicks(int ticks, double power);

    public abstract void moveByInches(double power, double inches, double timeoutSeconds);
    public abstract void moveByInches(double power, double leftInches, double rightInches, double timeoutSeconds);

    public abstract void turnDegrees(TurnDirection turnDirection, int degrees, double power, double timeoutSeconds);

    public abstract void stop();

    public abstract void setMode(DcMotor.RunMode runMode);

    public abstract void driveToPosition(int leftPosition, int rightPosition, double power);
}