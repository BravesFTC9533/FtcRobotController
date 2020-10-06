package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.data.Config;
import org.firstinspires.ftc.teamcode.data.SimpleMenu;

@Autonomous(name="Configuration", group="Utilities")
public class ConfigOpMode extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    public static SimpleMenu menu = new SimpleMenu("Configuration Menu");

    @Override
    public void runOpMode() {
        Config config = new Config(hardwareMap.appContext);

        menu.clearOptions();

        menu.addOption("Alliance Color", Config.AllianceColor.class, Config.allianceColor);
        menu.addOption("Starting Position", Config.StartingPosition.class, Config.startingPosition);
        menu.addOption("Max Lift Ticks", 10000, 0, 1, Config.maxLiftTicks);
        menu.addOption("Launcher Angle Low", 10000, 0, 1, Config.launcherAngleLow);
        menu.addOption("Launcher Angle Mid", 10000, 0, 1, Config.launcherAngleMid);
        menu.addOption("Launcher Angle High", 10000, 0, 1, Config.launcherAngleHigh);

        menu.setGamepad(gamepad1);
        menu.setTelemetry(telemetry);

        waitForStart();
        runtime.reset();

        while(opModeIsActive()) {
            menu.displayMenu();

            switch (menu.getCurrentChoiceOf("Alliance Color")) {
                case "RED": Config.allianceColor = Config.AllianceColor.RED;        break;
                case "BLUE": Config.allianceColor = Config.AllianceColor.BLUE;      break;
            }

            switch (menu.getCurrentChoiceOf("Starting Position")) {
                case "INNER": Config.startingPosition = Config.StartingPosition.INNER;      break;
                case "OUTER": Config.startingPosition = Config.StartingPosition.OUTER;      break;
            }

            Config.maxLiftTicks = (int) Double.parseDouble(menu.getCurrentChoiceOf("Max Lift Ticks"));
            Config.launcherAngleLow = (int) Double.parseDouble(menu.getCurrentChoiceOf("Launcher Angle Low"));
            Config.launcherAngleMid = (int) Double.parseDouble(menu.getCurrentChoiceOf("Launcher Angle Mid"));
            Config.launcherAngleHigh = (int) Double.parseDouble(menu.getCurrentChoiceOf("Launcher Angle High"));

            sleep(50);
        }

        config.save();
    }
}
