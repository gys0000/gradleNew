package com.gystry.buildsrc

import org.gradle.api.Plugin
import org.gradle.api.Project

class MyPlugin implements Plugin<Project>{
    @Override
    void apply(Project project) {
        def ext = project.extensions.create('gplugin', GPBean)
        //在当前project配置完成之后执行
        project.afterEvaluate {
            println "hello-->${ext.user}!!!"
        }
    }
}
