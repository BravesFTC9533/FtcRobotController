package org.firstinspires.ftc.teamcode.drive.vuforiaManagers;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.teamcode.drive.MecanumDrive;

public class MecanumVuforiaDriveManager extends AbstractVuforiaDriveManager<MecanumDrive> {

    public MecanumVuforiaDriveManager(MecanumDrive drive) {
        super(drive);
    }

    @Override
    public void driveToCurrentTarget(OpenGLMatrix lastLocation, double distanceInches) {

    }
}
