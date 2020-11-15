package org.firstinspires.ftc.teamcode.utils;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name="Max Velocity", group="Util Opmode")
@Disabled
public class MaxVelocityTest extends LinearOpMode {

    @Override
    public void runOpMode() {

        double maxVelocity = 0.0;
        double currentVelocity;

        telemetry.addData("Status", "Initializing");
        telemetry.update();

        DcMotorEx motor = hardwareMap.get(DcMotorEx.class, "front_left");

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        telemetry.addLine("Motor labeled front_left on start will rotate at max power.");
        telemetry.addLine("Disclaimer: Make sure that the battery is charged!!!!");
        telemetry.update();

        waitForStart();

        telemetry.addData("Status", "Running");
        telemetry.update();

        motor.setPower(1);

        while (opModeIsActive()) {

            currentVelocity = motor.getVelocity();

            if (currentVelocity > maxVelocity) {
                maxVelocity = currentVelocity;
            }

            telemetry.addData("Current Velocity", currentVelocity);
            telemetry.addData("Maximum Velocity", maxVelocity);
            telemetry.update();

        }

    }

}