package org.firstinspires.ftc.teamcode.drive.vuforiaManagers;

import org.firstinspires.ftc.teamcode.VuforiaManager;
import org.firstinspires.ftc.teamcode.drive.IDrive;

public abstract class AbstractVuforiaDriveManager<T extends IDrive> {

    protected final T drive;

    public AbstractVuforiaDriveManager(T drive) {
        this.drive = drive;
    }

    public abstract void driveToCurrentTarget(VuforiaManager vuforiaManager, double distance);

}
