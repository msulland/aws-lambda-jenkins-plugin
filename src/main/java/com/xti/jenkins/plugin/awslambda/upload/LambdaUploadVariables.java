package com.xti.jenkins.plugin.awslambda.upload;

/*
 * #%L
 * AWS Lambda Upload Plugin
 * %%
 * Copyright (C) 2015 XT-i
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */

import com.xti.jenkins.plugin.awslambda.AWSLambdaDescriptor;
import com.xti.jenkins.plugin.awslambda.util.LambdaClientConfig;
import com.xti.jenkins.plugin.awslambda.util.Tokenizer;
import hudson.EnvVars;
import hudson.Extension;
import hudson.Util;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Items;
import hudson.util.FormValidation;
import hudson.util.ListBoxModel;
import hudson.util.Secret;
import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.QueryParameter;

/**
 * Describable containing Lambda post build action config, checking feasability of migrating it to upload package.
 */
public class LambdaUploadVariables extends AbstractDescribableImpl<LambdaUploadVariables> {
    private boolean useInstanceCredentials;
    private String awsAccessKeyId;
    private String awsSecretKey;
    private String clearTextAwsSecretKey;
    private String awsRegion;
    private String artifactLocation;
    private String description;
    private String functionName;
    private String handler;
    private String memorySize;
    private String role;
    private String runtime;
    private String timeout;
    private boolean successOnly;
    private boolean publish;
    private String updateMode;
    private String alias;
    private boolean createAlias;
    private String subnets;
    private String securityGroups;

    @DataBoundConstructor
    public LambdaUploadVariables(String awsRegion, String functionName, String updateMode){
        this.awsRegion = awsRegion;
        this.functionName = functionName;
        this.updateMode = updateMode;
    }

    @Deprecated
    public LambdaUploadVariables(boolean useInstanceCredentials, String awsAccessKeyId, Secret awsSecretKey, String awsRegion, String artifactLocation, String description, String functionName, String handler, String memorySize, String role, String runtime, String timeout, boolean successOnly, boolean publish, String updateMode, String alias, boolean createAlias, String subnets, String securityGroups) {
        this.useInstanceCredentials = useInstanceCredentials;
        this.awsAccessKeyId = awsAccessKeyId;
        this.awsSecretKey = awsSecretKey != null ? awsSecretKey.getEncryptedValue() : null;
        this.awsRegion = awsRegion;
        this.artifactLocation = artifactLocation;
        this.description = description;
        this.functionName = functionName;
        this.handler = handler;
        this.memorySize = memorySize;
        this.role = role;
        this.runtime = runtime;
        this.timeout = timeout;
        this.successOnly = successOnly;
        this.publish = publish;
        this.updateMode = updateMode;
        this.alias = alias;
        this.createAlias = createAlias;
        this.subnets = subnets;
        this.securityGroups = securityGroups;
    }

    public boolean getUseInstanceCredentials() {
        return useInstanceCredentials;
    }

    @DataBoundSetter
    public void setUseInstanceCredentials(boolean useInstanceCredentials) {
        this.useInstanceCredentials = useInstanceCredentials;
    }

    public String getAwsAccessKeyId() {
        return awsAccessKeyId;
    }

    @DataBoundSetter
    public void setAwsAccessKeyId(String awsAccessKeyId) {
        this.awsAccessKeyId = awsAccessKeyId;
    }

    public String getAwsSecretKey() {
        return awsSecretKey;
    }

    @DataBoundSetter
    public void setAwsSecretKey(String awsSecretKey) {
        this.awsSecretKey = Secret.fromString(awsSecretKey).getEncryptedValue();
    }

    public String getAwsRegion() {
        return awsRegion;
    }

    public String getArtifactLocation() {
        return artifactLocation;
    }

    @DataBoundSetter
    public void setArtifactLocation(String artifactLocation) {
        this.artifactLocation = artifactLocation;
    }

    public String getDescription() {
        return description;
    }

    @DataBoundSetter
    public void setDescription(String description) {
        this.description = description;
    }

    public String getFunctionName() {
        return functionName;
    }

    public String getHandler() {
        return handler;
    }

    @DataBoundSetter
    public void setHandler(String handler) {
        this.handler = handler;
    }

    public String getMemorySize() {
        return memorySize;
    }

    @DataBoundSetter
    public void setMemorySize(String memorySize) {
        this.memorySize = memorySize;
    }

    public String getRole() {
        return role;
    }

    @DataBoundSetter
    public void setRole(String role) {
        this.role = role;
    }

    public String getRuntime() {
        return runtime;
    }

    @DataBoundSetter
    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getTimeout() {
        return timeout;
    }

    @DataBoundSetter
    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public boolean getSuccessOnly() {
        return successOnly;
    }

    @DataBoundSetter
    public void setSuccessOnly(boolean successOnly) {
        this.successOnly = successOnly;
    }

    public boolean getPublish() {
        return publish;
    }

    @DataBoundSetter
    public void setPublish(boolean publish) {
        this.publish = publish;
    }

    public String getUpdateMode() {
        return updateMode;
    }

    public String getAlias() {
        return alias;
    }

    @DataBoundSetter
    public void setAlias(String alias) {
        this.alias = alias;
    }

    public boolean getCreateAlias() {
        return createAlias;
    }

    @DataBoundSetter
    public void setCreateAlias(boolean createAlias) {
        this.createAlias = createAlias;
    }

    public String getSubnets() {
        return subnets;
    }

    @DataBoundSetter
    public void setSubnets(String subnets) {
        this.subnets = subnets;
    }

    public String getSecurityGroups() {
        return securityGroups;
    }

    @DataBoundSetter
    public void setSecurityGroups(String securityGroups) {
        this.securityGroups = securityGroups;
    }

    public void expandVariables(EnvVars env) {
        awsAccessKeyId = expand(awsAccessKeyId, env);
        clearTextAwsSecretKey = expand(Secret.toString(Secret.fromString(awsSecretKey)), env);
        awsRegion = expand(awsRegion, env);
        artifactLocation = expand(artifactLocation, env);
        description = expand(description, env);
        functionName = expand(functionName, env);
        handler = expand(handler, env);
        role = expand(role, env);
        runtime = expand(runtime, env);
        memorySize = expand(memorySize, env);
        timeout = expand(timeout, env);
        alias = expand(alias, env);
        subnets = expand(subnets, env);
        securityGroups = expand(securityGroups, env);
    }

    public LambdaUploadVariables getClone(){
        LambdaUploadVariables lambdaUploadVariables = new LambdaUploadVariables(awsRegion, functionName, updateMode);
        lambdaUploadVariables.setUseInstanceCredentials(useInstanceCredentials);
        lambdaUploadVariables.setAwsAccessKeyId(awsAccessKeyId);
        lambdaUploadVariables.setAwsSecretKey(awsSecretKey);
        lambdaUploadVariables.setArtifactLocation(artifactLocation);
        lambdaUploadVariables.setDescription(description);
        lambdaUploadVariables.setHandler(handler);
        lambdaUploadVariables.setMemorySize(memorySize);
        lambdaUploadVariables.setRole(role);
        lambdaUploadVariables.setRuntime(runtime);
        lambdaUploadVariables.setTimeout(timeout);
        lambdaUploadVariables.setSuccessOnly(successOnly);
        lambdaUploadVariables.setPublish(publish);
        lambdaUploadVariables.setAlias(alias);
        lambdaUploadVariables.setCreateAlias(createAlias);
        lambdaUploadVariables.setSubnets(subnets);
        lambdaUploadVariables.setSecurityGroups(securityGroups);
        return lambdaUploadVariables;
    }

    public DeployConfig getUploadConfig(){
        return new DeployConfig(artifactLocation, description, functionName, handler, StringUtils.isNotBlank(memorySize) ? Integer.valueOf(memorySize) : null, role, runtime, StringUtils.isNotBlank(timeout) ? Integer.valueOf(timeout) : null, updateMode, publish, alias, createAlias, Tokenizer.split(subnets), Tokenizer.split(securityGroups));
    }

    public LambdaClientConfig getLambdaClientConfig(){
        if(useInstanceCredentials){
            return new LambdaClientConfig(awsRegion);
        } else {
            return new LambdaClientConfig(awsAccessKeyId, clearTextAwsSecretKey, awsRegion);
        }
    }

    private String expand(String value, EnvVars env) {
        if(value != null) {
            return Util.replaceMacro(value.trim(), env);
        } else {
            return null;
        }
    }

    @Extension // This indicates to Jenkins that this is an implementation of an extension point.
    public static class DescriptorImpl extends AWSLambdaDescriptor<LambdaUploadVariables> {

        static{
            Items.XSTREAM2.addCompatibilityAlias("com.xti.jenkins.plugin.awslambda.LambdaVariables", com.xti.jenkins.plugin.awslambda.upload.LambdaUploadVariables.class);
        }

        public FormValidation doCheckTimeout(@QueryParameter String value, @QueryParameter String updateMode) {
            UpdateModeValue updateModeValue = UpdateModeValue.fromString(updateMode);
            if(updateModeValue == UpdateModeValue.Full || updateModeValue == UpdateModeValue.Config) {

                try {
                    Integer.parseInt(value);
                    return FormValidation.ok();
                } catch (NumberFormatException e) {
                    return FormValidation.warning("Not a number, might evaluate to number as environment variable.");
                }
            } else {
                return FormValidation.ok();
            }
        }

        public FormValidation doCheckMemorySize(@QueryParameter String value, @QueryParameter String updateMode) {
            UpdateModeValue updateModeValue = UpdateModeValue.fromString(updateMode);
            if(updateModeValue == UpdateModeValue.Full || updateModeValue == UpdateModeValue.Config) {
                try {
                    Integer.parseInt(value);
                    return FormValidation.ok();
                } catch (NumberFormatException e) {
                    return FormValidation.warning("Not a number, might evaluate to number as environment variable.");
                }
            } else {
                return FormValidation.ok();
            }
        }

        public ListBoxModel doFillUpdateModeItems(@QueryParameter String updateMode) {
            ListBoxModel items = new ListBoxModel();
            for (UpdateModeValue updateModeValue : UpdateModeValue.values()) {
                items.add(new ListBoxModel.Option(updateModeValue.getDisplayName(), updateModeValue.getMode(), updateModeValue.getMode().equals(updateMode)));
            }
            return items;
        }

        /**
         * This human readable name is used in the configuration screen.
         */
        public String getDisplayName() {
            return "Deploy into Lambda";
        }

    }


}
