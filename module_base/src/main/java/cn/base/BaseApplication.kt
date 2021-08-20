package cn.base

import android.app.Application
import android.content.Context
import cn.lib.base.BaseConfig
import cn.lib.base.ex.configWebViewCacheDirWithAndroidP
import com.blankj.utilcode.util.Utils
import com.xiaojinzi.component.Component
import com.xiaojinzi.component.Config

abstract class BaseApplication : Application(), BaseConfig {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        BaseConfig.config = this
    }

    override fun onCreate() {
        super.onCreate()

        initSdk()
        configWebViewCacheDirWithAndroidP(packageName)
    }

    open fun initSdk() {
        Utils.init(this)

        initComponent()
    }

    private fun initComponent() {
        // 初始化
        Component.init(
            isDebug(),
            Config.with(this)
                .defaultScheme(routerName())
                // 使用内置的路由重复检查的拦截器, 如果为 true,
                // 那么当两个相同的路由发生在指定的时间内后一个路由就会被拦截
                .useRouteRepeatCheckInterceptor(true)
                // 1000 是默认的, 表示相同路由拦截的时间间隔
                .routeRepeatCheckDuration(500)
                // 是否打印日志提醒你哪些路由使用了 Application 为 Context 进行跳转
                .tipWhenUseApplication(true)
                // 这里表示使用 ASM 字节码技术加载模块, 默认是 false
                // 如果是 true 请务必配套使用 Gradle 插件, 下一步就是可选的配置 Gradle 插件
                // 如果是 false 请直接略过下一步 Gradle 的配置
                .optimizeInit(true)
                // 自动加载所有模块, 打开此开关后下面无需手动注册了
                // 但是这个依赖 optimizeInit(true) 才会生效
                .autoRegisterModule(true) // 1.7.9+
                .build()
        )
        // 自动加载所有模块, 此功能需要打开开关 optimizeInit(true).
        // 如果你同时也打开了开关 autoRegisterModule(true), 那么这句代码也可省略了, 因为初始化的时候自动帮你注册了
        // 当然你也可以设置 autoRegisterModule(false), 然后自己选择时机去调用自动注册
        // ModuleManager.getInstance().autoRegister(); // 1.7.9+

        // 框架还带有检查重复的路由和重复的拦截器等功能,在 `debug` 的时候开启它
        Component.check()
    }
}