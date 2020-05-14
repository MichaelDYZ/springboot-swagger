# springboot-swagger
该项目展示了SpringBoot集成原生 swagger ，自动生成 API 文档。
一.创建新的springboot项目，引入pom文件。
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.2.7.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.dyz</groupId>
    <artifactId>swagger</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>swagger</name>
    <description>Demo project for Spring Boot</description>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>


        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>2.9.2</version>
        </dependency>

        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>2.9.2</version>
        </dependency>


        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
二、修改application.yml：
server:
  port: 8080
  servlet:
    context-path: /demo
 三、创建配置类
/**
 * @author dyz
 * @version 1.0
 * @date 2020/5/13 15:17
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.dyz.swagger.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("springboot-swagger")
                .description("这是一个 Swagger API Demo")
                .contact(new Contact("dyz", "https://blog.csdn.net/MICHAELKING1", "137025xxx@qq.com"))
                .version("1.0.0-SNAPSHOT")
                .build();
    }

}
四、配置通用API返回
/**
 * @author dyz
 * @version 1.0
 * @date 2020/5/13 15:09
 * 通用API返回
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "通用API接口返回", description = "Common Api Response")
public class ApiResponse<T> implements Serializable {
    private static final long serialVersionUID = -8987146499044811408L;
    /**
     * 通用返回状态
     */
    @ApiModelProperty(value = "通用返回状态", required = true)
    private Integer code;
    /**
     * 通用返回信息
     */
    @ApiModelProperty(value = "通用返回信息", required = true)
    private String message;
    /**
     * 通用返回数据
     */
    @ApiModelProperty(value = "通用返回数据", required = true)
    private T data;
}
 五、创建配置@ApiImplicitParam 的 dataType 属性
/**
 * @author dyz
 * @version 1.0
 * @date 2020/5/13 15:15
 *
 * 配置@ApiImplicitParam 的 dataType 属性使用
 */
public final class DataType {

    public final static String STRING = "String";
    public final static String INT = "int";
    public final static String LONG = "long";
    public final static String DOUBLE = "double";
    public final static String FLOAT = "float";
    public final static String BYTE = "byte";
    public final static String BOOLEAN = "boolean";
    public final static String ARRAY = "array";
    public final static String BINARY = "binary";
    public final static String DATETIME = "dateTime";
    public final static String PASSWORD = "password";

}
 六、创建配置 @ApiImplicitParam 的 paramType 属性使用
/**
 * @author dyz
 * @version 1.0
 * @date 2020/5/13 15:19
 * 配置 @ApiImplicitParam 的 paramType 属性使用
 */
public final class ParamType {

    public final static String QUERY = "query";
    public final static String HEADER = "header";
    public final static String PATH = "path";
    public final static String BODY = "body";
    public final static String FORM = "form";

}
七、创建一个实体类
/**
 * @author dyz
 * @version 1.0
 * @date 2020/5/13 15:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "用户实体类", description = "User")
public class User implements Serializable {
    private static final long serialVersionUID = 5057954049311281252L;

    @ApiModelProperty(value = "id", required = true)
    private Integer id;

    @ApiModelProperty(value = "用户名", required = true)
    private String name;

    @ApiModelProperty(value = "性别", required = true)
    private String sex;
}
八、创建测试Controller
/**
 * @author dyz
 * @version 1.0
 * @date 2020/5/13 16:00
 */
@RestController
@RequestMapping("/test")
@Api(tags = "1.0", description = "用户管理", value = "用户管理")
public class TestController {
    @GetMapping
    @ApiOperation(value = "条件查询", notes = "备注")
    @ApiImplicitParams({@ApiImplicitParam(name = "name", value = "用户名", dataType = DataType.STRING, paramType = ParamType.QUERY, defaultValue = "dyz")})
    public ApiResponse<User> getByUserName(String name) {
        System.out.println("多个参数用  @ApiImplicitParams");
        return ApiResponse.<User>builder().code(200)
                .message("操作成功")
                .data(new User(1, name, "男"))
                .build();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除用户", notes = "备注")
    @ApiImplicitParam(name = "id", value = "用户编号",  paramType = ParamType.PATH)
    public void delete(@PathVariable Integer id) {
        System.out.println("删除用户");
    }


    @PostMapping
    @ApiOperation(value = "添加用户")
    public User post(@RequestBody User user) {
        System.out.println("添加用户");
        return user;
    }

    @PostMapping("/{id}/file")
    @ApiOperation(value = "上传文件")
    public String file(@PathVariable Integer id, @RequestParam("file") MultipartFile file) {
        System.out.println(file.getContentType());
        System.out.println(file.getName());
        System.out.println(file.getOriginalFilename());
        return file.getOriginalFilename();
    }




}
九、启动项目，地址栏输入：http://localhost:8080/demo/swagger-ui.html#/1.0






十、源码地址：https://github.com/MichaelDYZ/springboot-swagger
