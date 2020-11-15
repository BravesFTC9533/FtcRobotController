package org.firstinspires.ftc.teamcode.common;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Robot {

    public static final int MAX_VELOCITY = 3120; // Run the max velocity test to figure out
    public static final int TARGET_TO_POSITION_TOLERANCE = 35;

    public static final double ROBOT_LENGTH = 16.15; // Length in inches of the chassis eg.

    public static final double            COUNTS_PER_MOTOR_REV    = 28;              // eg: NeveRest Side motor
    public static final double            DRIVE_GEAR_REDUCTION    = 40.0 / 1.0;      // This is < 1.0 if geared UP
    public static final double            WHEEL_DIAMETER_INCHES   = 2.95;            // For figuring circumference
    public static final double     COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * Math.PI);

    public final DcMotorEx frontLeft;
    public final DcMotorEx frontRight;
    public final DcMotorEx backLeft;
    public final DcMotorEx backRight;

    public final BNO055IMU imu;

    public final DcMotorEx[] allMotors;
    public final DcMotorEx[] leftMotors;
    public final DcMotorEx[] rightMotors;

    private boolean isReversed = false;

    public Robot(HardwareMap hardwareMap) {
        frontLeft = hardwareMap.get(DcMotorEx.class, "front_left");
        frontRight = hardwareMap.get(DcMotorEx.class, "front_right");
        backLeft = hardwareMap.get(DcMotorEx.class, "back_left");
        backRight = hardwareMap.get(DcMotorEx.class, "back_right");

        this.allMotors = new DcMotorEx[] { frontLeft, frontRight, backLeft, backRight };
        this.rightMotors = new DcMotorEx[] { frontRight, backRight };
        this.leftMotors = new DcMotorEx[] { frontLeft, backLeft };

        this.frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        this.backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        this.frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.frontLeft.setTargetPositionTolerance(TARGET_TO_POSITION_TOLERANCE);
        this.frontRight.setTargetPositionTolerance(TARGET_TO_POSITION_TOLERANCE);
        this.backLeft.setTargetPositionTolerance(TARGET_TO_POSITION_TOLERANCE);
        this.backRight.setTargetPositionTolerance(TARGET_TO_POSITION_TOLERANCE);

        

        this.setMotorMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void setMotorMode(DcMotor.RunMode runMode) {
        this.frontLeft.setMode(runMode);
        this.frontRight.setMode(runMode);
        this.backLeft.setMode(runMode);
        this.backRight.setMode(runMode);
    }

    public boolean isBusy() { return frontLeft.isBusy() || frontRight.isBusy() || backLeft.isBusy() || backRight.isBusy(); }

    public void setIsReverse(boolean isReverse) {
        this.isReversed = isReverse;

        for(DcMotorEx motor : allMotors) { motor.setDirection(motor.getDirection().inverted()); }
    }

    public boolean isReverse() { return isReversed; }

    public void stop() {
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }

    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior zeroPowerBehavior) {
        for(DcMotorEx motor : allMotors) motor.setZeroPowerBehavior(zeroPowerBehavior);
    }
}
