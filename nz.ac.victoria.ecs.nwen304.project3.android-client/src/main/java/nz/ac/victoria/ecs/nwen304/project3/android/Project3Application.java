package nz.ac.victoria.ecs.nwen304.project3.android;

import java.util.List;

import roboguice.application.RoboApplication;

import com.google.inject.Module;

public final class Project3Application extends RoboApplication {
    private static Project3Application instance;
    
    public Project3Application() {
            super();
            instance = this;
    }
    
    public static final Project3Application getInstance() {
            return instance;
    }
    
    @Override
    protected void addApplicationModules(final List<Module> modules) {
    }
}
