package com.shooter.mojbackend.judge.codesandbox.impl;

import com.shooter.mojbackend.enums.QuestionSubmitLanguageEnum;
import com.shooter.mojbackend.judge.codesandbox.CodeSandbox;
import com.shooter.mojbackend.judge.codesandbox.CodeSandboxFactory;
import com.shooter.mojbackend.judge.codesandbox.CodeSandboxProxy;
import com.shooter.mojbackend.judge.codesandbox.model.ExecuteCodeRequest;
import com.shooter.mojbackend.judge.codesandbox.model.ExecuteCodeResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ClassName: ExampleCodeSandboxTest
 * Package: com.shooter.mojbackend.judge.codesandbox.impl
 * Description:
 *
 * @Author:Shooter
 * @Create 2024/2/27 8:57
 * @Version 1.0
 */
@SpringBootTest
class ExampleCodeSandboxTest {

    @Value("${codesandbox.type}")
    private String type;

    @Test
    void executeCodeByValue() {
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        codeSandbox = new CodeSandboxProxy(codeSandbox);
        String code = "public class Main {\n" +
                "    public static void main(String[] args) {\n" +
                "        int a = Integer.parseInt(args[0]);\n" +
                "        int b = Integer.parseInt(args[1]);\n" +
                "        System.out.println(\"结果:\" + (a + b));\n" +
                "    }\n" +
                "}";
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        List<String> inputList = Arrays.asList("1 2", "3 4");
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        Assertions.assertNotNull(executeCodeResponse);
    }

}