package org.firstinspires.ftc.teamcode.drive.gyroManagers;

import android.graphics.drawable.GradientDrawable;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.drive.AbstractDrive;
import org.firstinspires.ftc.teamcode.drive.MecanumDrive;

public class MecanumGyroDriveManager extends AbstractGyroDriveManager<MecanumDrive> {

    public MecanumGyroDriveManager(MecanumDrive drive, LinearOpMode opMode, BNO055IMU imu) {
        super(drive, opMode, imu);
    }

    @Override
    public void moveByInches(double power, double inches, double timeoutSeconds) {
    }

    @Override
    public void turnDegrees(AbstractDrive.TurnDirection turnDirection, double power, int degrees, double timeoutSeconds) {
        ElapsedTime timer = new ElapsedTime();
        timer.reset();

        if(turnDirection == AbstractDrive.TurnDirection.CLOCKWISE) {
            degrees = -degrees;
        } else power = -power;

        Orientation startOrientation = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        float targetOrientation = startOrientation.firstAngle + degrees;

        opMode.telemetry.log().add("Target Orientation: " + targetOrientation);
        opMode.telemetry.log().add("Start Orientation: " + startOrientation.firstAngle);
        opMode.telemetry.update();

        while(opMode.opModeIsActive() && timer.seconds() <= timeoutSeconds) {
            if(turnDirection == AbstractDrive.TurnDirection.CLOCKWISE) {
                if(imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle <= targetOrientation) break;
                else drive.drive(0, 0, power);
            }


            opMode.telemetry.addData("Orientation", imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle);
            opMode.telemetry.update();
        }
    }

}
