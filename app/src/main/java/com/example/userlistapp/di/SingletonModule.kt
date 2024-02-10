package com.example.userlistapp.di

import com.example.userlistapp.data.datasource.DataSource
import com.example.userlistapp.data.datasource.DataSourceImpl
import com.example.userlistapp.data.repository.RepositoryImpl
import com.example.userlistapp.domain.repository.Repository
import com.example.userlistapp.domain.usecase.GetItemListUseCase
import com.example.userlistapp.domain.usecase.UpdateItemUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {

	@Provides
	@Singleton
	fun provideDataSource(): DataSource {
		return DataSourceImpl()
	}

	@Provides
	@Singleton
	fun provideRepository(dataSource: DataSource): Repository {
		return RepositoryImpl(dataSource)
	}

	@Provides
	@Singleton
	fun provideGetItemListUseCase(repository: Repository): GetItemListUseCase {
		return GetItemListUseCase(repository)
	}

	@Provides
	@Singleton
	fun provideUpdateItemUseCase(repository: Repository): UpdateItemUseCase {
		return UpdateItemUseCase(repository)
	}
}