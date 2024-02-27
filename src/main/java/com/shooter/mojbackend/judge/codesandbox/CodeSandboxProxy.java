package com.shooter.mojbackend.judge.codesandbox;

import com.shooter.mojbackend.judge.codesandbox.model.ExecuteCodeRequest;
import com.shooter.mojbackend.judge.codesandbox.model.ExecuteCodeResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.apache.bcel.classfile.Code;

/**
 * ClassName: CodeSandboxProxy
 * Package: com.shooter.mojbackend.judge.codesandbox
 * Description:
 *
 * @Author:Shooter
 * @Create 2024/2/27 9:25
 * @Version 1.0
 */
@Slf4j
public class CodeSandboxProxy implements CodeSandbox {

    private CodeSandbox codeSandbox;

    public CodeSandboxProxy(CodeSandbox codeSandbox) {
        this.codeSandbox = codeSandbox;
    }

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("代码沙箱请求信息：" + executeCodeRequest.toString());
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        log.info("代码沙箱响应信息：" + executeCodeResponse.toString());
        return executeCodeResponse;
    }
}
