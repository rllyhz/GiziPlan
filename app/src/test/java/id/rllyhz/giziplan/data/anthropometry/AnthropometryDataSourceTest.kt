package id.rllyhz.giziplan.data.anthropometry

import id.rllyhz.giziplan.data.anthropometry.core.AnthropometryDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner.Silent::class)
class AnthropometryDataSourceTest {
    private lateinit var dataSource: AnthropometryDataSource

    @Mock
    private lateinit var anthropometryDao: AnthropometryDao

    @Before
    fun setup() {
        dataSource = AnthropometryDataSource(anthropometryDao)
    }
}