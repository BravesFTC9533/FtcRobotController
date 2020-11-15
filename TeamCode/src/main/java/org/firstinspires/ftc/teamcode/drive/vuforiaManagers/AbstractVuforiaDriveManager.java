package org.firstinspires.ftc.teamcode.drive.vuforiaManagers;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.teamcode.drive.AbstractDrive;

public abstract class AbstractVuforiaDriveManager<T extends AbstractDrive> {

    protected final T drive;

    public AbstractVuforiaDriveManager(T drive) {
        this.drive = drive;
    }

    public abstract void driveToCurrentTarget(OpenGLMatrix lastLocation, double distance);

}
