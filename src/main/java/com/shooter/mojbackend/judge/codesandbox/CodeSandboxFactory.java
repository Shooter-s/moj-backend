package com.shooter.mojbackend.judge.codesandbox;

import com.shooter.mojbackend.judge.codesandbox.impl.ExampleCodeSandbox;
import com.shooter.mojbackend.judge.codesandbox.impl.RemoteCodeSandbox;
import com.shooter.mojbackend.judge.codesandbox.impl.ThirdPatyCodeSandbox;

/**
 * ClassName: CodeSandboxFactory
 * Package: com.shooter.mojbackend.judge.codesandbox
 * Description: 代码沙箱简单工厂
 *
 * @Author:Shooter
 * @Create 2024/2/27 9:07
 * @Version 1.0
 */
public class CodeSandboxFactory {

    /**
     * 创建代码沙箱示例
     *
     * @param type 沙箱类型
     * @return
     */
    public static CodeSandbox newInstance(String type) {
        switch (type) {
            case "example":
                return new ExampleCodeSandbox();
            case "remote":
                return new RemoteCodeSandbox();
            case "thirdParty":
                return new ThirdPatyCodeSandbox();
            default:
                return new ExampleCodeSandbox();
        }
    }

}
