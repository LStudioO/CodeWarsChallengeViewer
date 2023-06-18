package com.feature.user.data.remote.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.core.utils.logger.AppLogger
import com.feature.user.data.remote.api.UserApi
import com.feature.user.data.remote.dto.CompletedChallengeDto

internal class CompletedChallengesPagingSource(
    private val userApi: UserApi,
    private val userName: String,
    private val logger: AppLogger,
) : PagingSource<Int, CompletedChallengeDto>() {

    var counter = 0
    override fun getRefreshKey(state: PagingState<Int, CompletedChallengeDto>): Int? {
        val anchor = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchor) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CompletedChallengeDto> =
        try {
            val page = params.key ?: 0
            val size = params.loadSize
            val from = page * size
            logger.d("Page $page is loading")
            userApi.getCompletedChallenges(username = userName, page = page).fold(
                ifLeft = {
                    logger.e(it)
                    LoadResult.Error(it)
                },
                ifRight = { result ->
                    val nextKey = if (result.data.isEmpty() || result.totalPages <= page + 1) null else page + 1
                    val prevKey = if (page == 0) null else page - 1
                    if (params.placeholdersEnabled) {
                        val itemsAfter = minOf(
                            size,
                            result.totalItems - ((page + 1) * size),
                        ).coerceAtLeast(0)
                        logger.d("Next item bunch count: $itemsAfter")
                        LoadResult.Page(
                            data = result.data,
                            prevKey = prevKey,
                            nextKey = nextKey,
                            itemsAfter = itemsAfter,
                            itemsBefore = from,
                        )
                    } else {
                        LoadResult.Page(
                            data = result.data,
                            prevKey = prevKey,
                            nextKey = nextKey,
                        )
                    }
                },
            )
        } catch (e: Exception) {
            logger.e(e)
            LoadResult.Error(e)
        }
}
