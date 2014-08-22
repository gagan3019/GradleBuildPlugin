package com.example.pack;

import com.android.build.gradle.AppExtension;
import com.android.builder.core.DefaultBuildType;
import com.android.builder.core.DefaultProductFlavor;

import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtensionContainer;

import java.io.File;
import java.util.Arrays;

/**
 * Created by gagandeep on 19/8/14.
 */
public class ProjectSpecifics {

    private Project mProject;
    private String mModuleName;
    private String mBuildPath;
    private String[] mBuildTypes;
    private String[] mProductFlavours;
    private String mOutputApkDirPath;
    private CustomExtension mCurrentBuildConfig;
    public ProjectSpecifics(Project project) {
        this.mProject = project;
        mModuleName = mProject.getName();
        mBuildPath = mProject.getBuildDir().getAbsoluteFile().getAbsolutePath();
        ExtensionContainer extContainer = project.getExtensions();
        AppExtension appExtension = extContainer.getByType(AppExtension.class);
        NamedDomainObjectContainer<DefaultProductFlavor> flavours = appExtension.getProductFlavors();
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
        mOutputApkDirPath = mBuildPath+ File.separator+"outputs"+File.separator+"apk";
    }

    private void validatePaths() throws Exception {
        File outPutApkDir = new File(mOutputApkDirPath);
        if(!outPutApkDir.exists()) {
            throw new Exception("Apk is not generated yet , aborting operation");
        }
    }

    public String generateApk() {
        String apkPath = null;
        String[] tempApkFileNames = new String[mBuildTypes.length*mProductFlavours.length];
        String[] buildConfigs = new String[mBuildTypes.length*mProductFlavours.length];
        int counter = 0;
        for(int i = 0;i < mProductFlavours.length;i++) {
            for(int j = 0;j < mBuildTypes.length;j++) {
                tempApkFileNames[counter] = mModuleName+"-"+mProductFlavours[i]+"-"+mBuildTypes[j]+".apk";
                buildConfigs[counter] = mProductFlavours[i]+mBuildTypes[j].substring(0,1).toUpperCase()+mBuildTypes[j].substring(1)+"BuildConfig";
                counter++;
            }
        }
        File outPutApkDir = new File(mOutputApkDirPath);
        File[] files = outPutApkDir.listFiles();
        for(File file:files) {
            if(Arrays.asList(tempApkFileNames).contains(file.getName())) {
                apkPath = file.getAbsolutePath();
                int index = Arrays.asList(tempApkFileNames).indexOf(file.getName());
                ExtensionContainer extContainer = mProject.getExtensions();
                mCurrentBuildConfig = (CustomExtension) extContainer.getByName(buildConfigs[index]);
                System.out.println(mCurrentBuildConfig.toString());
                break;
            }
        }
        /*String[] apkFiles = outPutApkDir.list();
        for(String tempApkName:tempApkFileNames) {
            if(Arrays.asList(apkFiles).contains(tempApkName)) {
                apkPath =
            }
        }*/
        return apkPath;
    }

    @Override
    public String toString() {
        try {
            validatePaths();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return generateApk();
    }

    public Project getProject() {
        return mProject;
    }

    public String getModuleName() {
        return mModuleName;
    }

    public String getBuildPath() {
        return mBuildPath;
    }

    public String[] getBuildTypes() {
        return mBuildTypes;
    }

    public String[] getProductFlavours() {
        return mProductFlavours;
    }

    public String getmOutputApkDirPath() {
        return mOutputApkDirPath;
    }

    public CustomExtension getCurrentBuildConfig() {
        return mCurrentBuildConfig;
    }
}
