# spring项目运行方式
- 运行应用类
- mvn运行 mvn spring-boot:run
- java -jar 

# mybatis-generator 运行方式
### java -jar mybatis-generator-core-1.4.0.jar -configfile generatorConfig.xml -overwrite

# spring-boot 加载静态资源
@Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");
        super.addResourceHandlers(registry);
    }
}

# layui
### 弹出层数据表格 数据表格中使用弹窗