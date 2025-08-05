package com.madrid.presentation.workManager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class WorkerClass @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val viewModel: WorkManagerViewModel
) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        viewModel.clearHomeMoviesCacheData()
        return Result.success()
    }
}