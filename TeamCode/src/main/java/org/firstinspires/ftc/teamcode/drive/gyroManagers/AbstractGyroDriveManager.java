package org.firstinspires.ftc.teamcode.drive.gyroManagers;

import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.teamcode.drive.AbstractDrive;

public abstract class AbstractGyroDriveManager<T extends AbstractDrive> {

    protected T drive;
    protected BNO055IMU imu;

    public AbstractGyroDriveManager(T drive, BNO055IMU imu) {
        this.drive = drive;
        this.imu = imu;
    }

    public abstract void moveByInches(double power, double inches, double timeoutSeconds);

}
