# FileUpload
1.pom文件添加
2.pom文件jdk  版本编译错误 Idea 编译报错 javacTask: 源发行版 1.8 需要目标发行版 1.8  
<plugin>  
                <groupId>org.apache.maven.plugins</groupId>  
                <artifactId>maven-compiler-plugin</artifactId>  
                <version>3.3</version>  
                <configuration>  
                  <source>1.8</source>  
                  <target>1.8</target>  
                </configuration>  
            </plugin>  