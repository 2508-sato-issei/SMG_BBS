package com.example.SMG_BBS.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// FilterConfig => どのFilterをどのURLに機能させるのか、フィルター対象のURLを記述する。
// 設定を記述するときには@Configurationを付与する。
@Configuration
public class FilterConfig {

    // フィルターごとに@Beanを付与する。
    @Bean
    public FilterRegistrationBean<LoginFilter> loginFilter() {

        FilterRegistrationBean<LoginFilter> bean = new FilterRegistrationBean<>();

        // LoginFilterクラスのdoFilterメソッドがFilterChain内で実行される
        bean.setFilter(new LoginFilter());

        // ログインフィルターを適用するURLを設定（今回は全ページにした）
        bean.addUrlPatterns("/*");
        return bean;
    }

}
