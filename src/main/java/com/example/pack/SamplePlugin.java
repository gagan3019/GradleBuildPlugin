package com.example.pack;

import com.android.build.gradle.AppExtension;
import com.android.builder.core.DefaultBuildType;
import com.android.builder.core.DefaultProductFlavor;

import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.plugins.ExtensionAware;

public class SamplePlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        System.out.println("Inside Of SamplePlugin");
        ExtensionContainer extContainer = project.getExtensions();
        AppExtension appExtension = extContainer.getByType(AppExtension.class);
        NamedDomainObjectContainer<DefaultProductFlavor> flavours = appExtension.getProductFlavors();
        String[] mBuildTypes = null;
        String[] mProductFlavours = null;

        if(flavours != null && flavours.size() != 0) {
            if(mProductFlavours == null) {
                mProductFlavours = new String[flavours.size()];
            }
            int counter = 0;
            for(DefaultProductFlavor flavour:flavours) {
                mProductFlavours[counter] = flavour.getName();
                counter++;
            }
        }
        NamedDomainObjectContainer<DefaultBuildType> buildTypes = appExtension.getBuildTypes();
        if(buildTypes != null && buildTypes.size() != 0) {
            if(mBuildTypes == null) {
                mBuildTypes = new String[buildTypes.size()];
            }
            int counter = 0;
            for(DefaultBuildType buildType:buildTypes) {
                mBuildTypes[counter] = buildType.getName();
                counter++;
            }
        }
        for(int i = 0;i < mProductFlavours.length;i++) {
            for(int j = 0;j < mBuildTypes.length;j++) {
                String extensionName = mProductFlavours[i]+mBuildTypes[j].substring(0,1).toUpperCase()+mBuildTypes[j].substring(1)+"BuildConfig";
                System.out.println("Creating extension :: "+extensionName);
                project.getExtensions().create(extensionName,CustomExtension.class);

            }
        }
    }

    private void addTasks(Project project) {
        project.getTasks().create("SamplePlugin",SampleTask.class);
    }

}