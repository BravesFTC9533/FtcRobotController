package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.common.FtcGamePad;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.drive.gyroManagers.MecanumGyroDriveManager;
import org.firstinspires.ftc.teamcode.drive.vuforiaManagers.MecanumVuforiaDriveManager;


/**
 * Created by 9533 on 11/15/2020.
 */

public class MecanumDrive extends AbstractDrive {

    private static final double MIN_SPEED       = 0.2;
    protected static final float mmPerInch      = 25.4f;

    // Motors
    private final DcMotorEx fl;
    private final DcMotorEx fr;
    private final DcMotorEx bl;
    private final DcMotorEx br;

    private boolean halfDriveSpeed = false; // If true, teleop driving will be half speed.

    public MecanumDrive(LinearOpMode opMode, Robot robot) {
        super(opMode, robot);
        this.fl = robot.frontLeft;
        this.fr = robot.frontRight;
        this.bl = robot.backLeft;
        this.br = robot.backRight;

        this.vuforiaDriveManager = new MecanumVuforiaDriveManager(this);
        this.gyroDriveManager = new MecanumGyroDriveManager(this, robot.imu);
    }

    public boolean getIsReverse(){
        return robot.isReverse();
    }

    public void setIsReverse(boolean value){
        robot.setIsReverse(value);
    }

    @Deprecated
    public void gamePadButtonEvent(FtcGamePad gamepad, int button, boolean pressed) {}

    public void handle(FtcGamePad driverGamepad){
        double h, v, r;

        h = -driverGamepad.getLeftStickX() + Math.pow(driverGamepad.getLeftTrigger(), 3)
                - Math.pow(driverGamepad.getRightTrigger(), 3);
        v = -driverGamepad.getLeftStickY();
        r = driverGamepad.getRightStickX();

        if(Math.abs(h) < MIN_SPEED) {
            h = 0;
        }
        if(Math.abs(v) < MIN_SPEED) {
            v = 0;
        }
        if(Math.abs(r) < MIN_SPEED){
            r = 0;
        }

        if(robot.isReverse()) {
            h *= -1;
            v *= -1;
        }

        // add vectors
        double frontLeft =  v-h+r;
        double frontRight = v+h-r;
        double backRight =  v-h-r;
        double backLeft =   v+h+r;

        // since adding vectors can go over 1, figure out max to scale other wheels
        double max = Math.max(
                Math.abs(backLeft),
                Math.max(
                        Math.abs(backRight),
                        Math.max(
                                Math.abs(frontLeft), Math.abs(frontRight)
                        )
                )
        );
        // only need to scale power if max > 1
        if(max > 1){
            frontLeft = scalePower(frontLeft, max);
            frontRight = scalePower(frontRight, max);
            backLeft = scalePower(backLeft, max);
            backRight = scalePower(backRight, max);
        }

        fl.setVelocity(halfDriveSpeed ? frontLeft * Robot.MAX_VELOCITY / 2 : frontLeft * Robot.MAX_VELOCITY);
        fr.setVelocity(halfDriveSpeed ? frontRight * Robot.MAX_VELOCITY / 2 : frontRight * Robot.MAX_VELOCITY);
        bl.setVelocity(halfDriveSpeed ? backLeft * Robot.MAX_VELOCITY / 2 : backLeft * Robot.MAX_VELOCITY);
        br.setVelocity(halfDriveSpeed ? backRight * Robot.MAX_VELOCITY / 2 : backRight * Robot.MAX_VELOCITY);
    }

    @Override
    public void drive(double v, double h, double r) {
        // add vectors
        double frontLeft =  v-h+r;
        double frontRight = v+h-r;
        double backRight =  v-h-r;
        double backLeft =   v+h+r;

        // since adding vectors can go over 1, figure out max to scale other wheels
        double max = Math.max(
                Math.abs(backLeft),
                Math.max(
                        Math.abs(backRight),
                        Math.max(
                                Math.abs(frontLeft), Math.abs(frontRight)
                        )
                )
        );
        // only need to scale power if max > 1
        if(max > 1){
            frontLeft = scalePower(frontLeft, max);
            frontRight = scalePower(frontRight, max);
            backLeft = scalePower(backLeft, max);
            backRight = scalePower(backRight, max);
        }

        fl.setVelocity(frontLeft * Robot.MAX_VELOCITY);
        fr.setVelocity(frontRight * Robot.MAX_VELOCITY);
        bl.setVelocity(backLeft * Robot.MAX_VELOCITY);
        br.setVelocity(backRight * Robot.MAX_VELOCITY);

    }

    @Override
    public void drive(double left, double right) {
        fl.setVelocity(left * Robot.MAX_VELOCITY);
        bl.setVelocity(left * Robot.MAX_VELOCITY);
        br.setVelocity(right * Robot.MAX_VELOCITY);
        fr.setVelocity(right * Robot.MAX_VELOCITY);
    }

    @Override
    public void stop() {
        drive(0, 0, 0);
    }

    @Override
    public void setMode(DcMotor.RunMode runMode) {
        fl.setMode(runMode);
        fr.setMode(runMode);
        bl.setMode(runMode);
        br.setMode(runMode);
    }

    @Override
    public void driveToPosition(int leftPosition, int rightPosition, double power) {
        addTargetPositions(robot.leftMotors, leftPosition);
        addTargetPositions(robot.rightMotors, rightPosition);
        setMode(DcMotor.RunMode.RUN_TO_POSITION);

        setMotorsPowers(robot.allMotors, power);

        while(opMode.opModeIsActive() && robot.isBusy()) {}

        stop();
    }

    // Scale motor power based on the max for all wheels
    // 1, 1, 1, 3 will become .33, .33, .33, 1
    public static double scalePower(double value, double max){
        if(max == 0){return  0;}
        return  value / max;
    }

    // motor power clipping helper
    public static double clipMotorPower(double value){
        return Range.clip(value, -1, 1);
    }

    public static double scale(double power){
        int modifier = 1;

        if (power == 0 )
        {
            return 0;
        }

        if(power < 0){
            modifier *= -1;
        }

        return  (power * power * modifier);
    }

    public void addTargetPositions(DcMotorEx[] motors, int ticks) {
        for(DcMotorEx motor : motors) {
            motor.setTargetPosition(motor.getCurrentPosition() + ticks);
        }
    }

    public void setMotorsPowers(DcMotorEx[] motors, double power) {
        for(DcMotorEx motor : motors) {
            motor.setVelocity(power * Robot.MAX_VELOCITY);
        }
    }

    public void driveByEncoderTicks(int ticks, double power) {
        addTargetPositions(robot.allMotors, ticks);
        setMotorsPowers(robot.allMotors, power);
    }

    public void moveByInches(double power, double inches, double timeoutSeconds) {
        moveByInches(power, inches, inches, timeoutSeconds);
    }

    public void moveByInches(double power, double leftInches, double rightInches, double timeoutSeconds) {
        encoderDrive(power, (int) Robot.COUNTS_PER_INCH * leftInches,
                (int) Robot.COUNTS_PER_INCH * rightInches, timeoutSeconds);
    }

    public void turnDegrees(TurnDirection direction, int degrees, double power, double timeoutSeconds) {
        double inchesPerDegree = Robot.ROBOT_LENGTH / 90; // Find how many inches are in a degree
        degrees *= inchesPerDegree;

        if(direction == TurnDirection.COUNTER_CLOCKWISE) {
            degrees = -degrees;
        }

        moveByInches(power, degrees, -degrees, timeoutSeconds);
    }

    public void encoderDrive(double targetSpeed, double leftTicks, double rightTicks, double timeoutSeconds) {
        addTargetPositions(robot.leftMotors, (int) leftTicks);
        addTargetPositions(robot.rightMotors, (int) rightTicks);

        robot.setMotorMode(DcMotor.RunMode.RUN_TO_POSITION);

        setMotorsPowers(robot.allMotors, targetSpeed);

        ElapsedTime timer = new ElapsedTime();
        timer.reset();

        while(opMode.opModeIsActive() && robot.isBusy() && timer.seconds() <= timeoutSeconds) {}

        robot.stop();
        robot.setMotorMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public boolean isHalfDriveSpeed() {
        return halfDriveSpeed;
    }

    public void setHalfDriveSpeed(boolean halfDriveSpeed) {
        this.halfDriveSpeed = halfDriveSpeed;
    }

    public void toggleHalfDriveSpeed() { halfDriveSpeed = !halfDriveSpeed; }
}