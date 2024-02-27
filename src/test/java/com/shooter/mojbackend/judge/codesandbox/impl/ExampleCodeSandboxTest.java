package com.shooter.mojbackend.judge.codesandbox.impl;

import com.shooter.mojbackend.judge.codesandbox.CodeSandbox;
import com.shooter.mojbackend.judge.codesandbox.model.ExecuteCodeRequest;
import com.shooter.mojbackend.judge.codesandbox.model.ExecuteCodeResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

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

    @Test
    public void testSandbox(){
        CodeSandbox codeSandbox = new ExampleCodeSandbox();
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code("System")
                .inputList(Arrays.asList("1 2", "3 4"))
                .language("java")
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        Assertions.assertNotNull(executeCodeResponse);
    }

}