package com.silverpants.grocer.network

import com.silverpants.grocer.BuildConfig
import com.silverpants.grocer.data.Converters
import com.silverpants.grocer.data.auth.models.TokenModel
import com.silverpants.grocer.misc.Constants
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {
    var token: TokenModel? = null

    @Singleton
    @Provides
    fun provideAuthInterceptor(): Interceptor {
        return AccessTokenInterceptor()
    }

    @Singleton
    @Provides
    fun provideAuthAuthenticator(lazyWrapper: Lazy<RamenApiService>): Authenticator {
        return AccessTokenAuthenticator(lazyWrapper)
    }


    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    fun provideClient(
        authInterceptor: Interceptor,
        authenticator: Authenticator,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()

        //Debugging
        httpClientBuilder.addInterceptor(authInterceptor)
        httpClientBuilder.authenticator(authenticator)

        //Logging
        if (BuildConfig.DEBUG) {
            httpClientBuilder.addInterceptor(httpLoggingInterceptor)
        }
        return httpClientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder().apply {
        client(client)
        baseUrl(Constants.baseUrl)
        addConverterFactory(JacksonConverterFactory.create(Converters.objectMapper))
    }.build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): RamenApiService =
        retrofit.create(RamenApiService::class.java)
}