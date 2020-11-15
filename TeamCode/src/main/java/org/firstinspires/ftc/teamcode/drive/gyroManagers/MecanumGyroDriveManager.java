package org.firstinspires.ftc.teamcode.drive.gyroManagers;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.MecanumDrive;

public class MecanumGyroDriveManager extends AbstractGyroDriveManager<MecanumDrive> {

    public MecanumGyroDriveManager(MecanumDrive drive, LinearOpMode opMode, BNO055IMU imu) {
        super(drive, opMode, imu);
    }

    @Override
    public void moveByInches(double power, double inches, double timeoutSeconds) {

    }

}
