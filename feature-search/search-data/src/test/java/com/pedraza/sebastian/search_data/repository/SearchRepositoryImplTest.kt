package com.pedraza.sebastian.search_data.repository


import com.google.common.truth.Truth.assertThat
import com.pedraza.sebastian.search_data.api.MercadoLibreService
import com.pedraza.sebastian.search_data.datasource.remote.SearchRemoteDataSource
import com.pedraza.sebastian.search_data.datasource.remote.SearchRemoteDataSourceImpl
import com.pedraza.sebastian.core.utils.Result
import com.pedraza.sebastian.core.utils.UiText
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class SearchRepositoryImplTest {

    private lateinit var repository: SearchRepositoryImpl
    private lateinit var dataSource: SearchRemoteDataSource
    private lateinit var mockWebServer: MockWebServer
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var meliApiMock: MercadoLibreService


    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        okHttpClient = OkHttpClient.Builder()
            .writeTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .connectTimeout(1, TimeUnit.SECONDS)
            .build()

        meliApiMock = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(mockWebServer.url("/"))
            .build()
            .create(MercadoLibreService::class.java)

        dataSource = SearchRemoteDataSourceImpl(meliApiMock)

        repository = SearchRepositoryImpl(dataSource)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    private fun enqueueMockResponse(filename: String, code: Int = 200) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream(filename)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        mockResponse.setResponseCode(code)
        mockWebServer.enqueue(mockResponse)
    }

    @Test
    fun `test getCategories with valid response returns Success result`() = runBlocking {
        //given
        enqueueMockResponse("get_categories_success.json")
        //when
        val result = repository.getCategories("MCO")
        //then
        assertThat(result is Result.Success).isTrue()
        assertThat(result.data).isNotNull()
        with(result.data!![0]) {
            assertThat(id).isEqualTo("MCO1747")
            assertThat(name).isEqualTo("Accesorios para Vehículos")
        }
    }

    @Test
    fun `test getCategories with invalid response returns Error result`() = runBlocking {
        //given
        enqueueMockResponse("get_categories_success.json", 403)
        //when
        val result = repository.getCategories("MCO")
        //then
        assertThat(result is Result.Error).isTrue()
        assertThat(result.message is UiText.ResourcesString).isTrue()
        assertThat(result.data).isNull()
    }


    @Test
    fun `test getCategories with malformed response returns Error result`() = runBlocking {
        //given
        enqueueMockResponse("get_categories_malformed.json")
        //when
        val result = repository.getCategories("MCO")
        //then
        assertThat(result is Result.Error).isTrue()
        assertThat(result.message is UiText.DynamicString).isTrue()
        assertThat(result.data).isNull()
    }

    @Test
    fun `test getItemDetail with valid response returns Success result`() = runBlocking {
        //given
        enqueueMockResponse("get_itemdetail_success.json")
        enqueueMockResponse("get_itemdescription_success.json")
        //when
        val result = repository.getItemDetail("MCO1033116459")
        //then
        assertThat(result is Result.Success).isTrue()
        assertThat(result.data).isNotNull()
        with(result.data!!) {
            assertThat(id).isEqualTo("MCO1033116459")
            assertThat(description).contains("Con tu consola Xbox Series tendrás entretenimiento")
        }
    }

    @Test
    fun `test getItemDetail when one operation fails returns Error result`() = runBlocking {
        //given
        enqueueMockResponse("get_itemdetail_success.json", 403)
        enqueueMockResponse("get_itemdescription_success.json")
        //when
        val result = repository.getItemDetail("MCO1033116459")
        //then
        assertThat(result is Result.Error).isTrue()
        assertThat(result.message is UiText.ResourcesString).isTrue()
    }

    @Test
    fun `test getItemDetail with malformed response returns Error result`() = runBlocking {
        //given
        enqueueMockResponse("get_itemdetail_malformed.json")
        enqueueMockResponse("get_itemdescription_success.json")
        //when
        val result = repository.getItemDetail("MCO1033116459")
        //then
        assertThat(result is Result.Error).isTrue()
        assertThat(result.message is UiText.DynamicString).isTrue()
        assertThat(result.data).isNull()
    }

    @Test
    fun `test searchItems with valid response returns Success result`() = runBlocking {
        //given
        enqueueMockResponse("search_items_success.json")
        //when
        val result = repository.searchItems(
            siteId = "MCO",
            itemsPerPage = 10,
            pagingOffset = 0,
            category = null,
            query = "xbox"
        )
        //then
        assertThat(result is Result.Success).isTrue()
        assertThat(result.data).isNotNull()
        with(result.data!!.results[0]) {
            assertThat(thumbnail).isEqualTo("http://http2.mlstatic.com/D_995465-MLA45731835097_042021-I.jpg")
            assertThat(title).isEqualTo("Microsoft Xbox Series S 512gb Standard Color  Blanco")
        }
    }

    @Test
    fun `test searchItems with invalid response returns Error result`() = runBlocking {
        //given
        enqueueMockResponse("search_items_success.json", 403)
        //when
        val result = repository.searchItems(
            siteId = "MCO",
            itemsPerPage = 10,
            pagingOffset = 0,
            category = null,
            query = "xbox"
        )
        //then
        assertThat(result is Result.Error).isTrue()
        assertThat(result.message is UiText.ResourcesString).isTrue()
        assertThat(result.data).isNull()

    }

    @Test
    fun `test searchItems with empty list returns Success result`() = runBlocking {
        //given
        enqueueMockResponse("search_items_empty.json")
        //when
        val result = repository.searchItems(
            siteId = "MCO",
            itemsPerPage = 10,
            pagingOffset = 0,
            category = null,
            query = "xbox"
        )
        //then
        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat(result.data).isNotNull()
        assertThat(result.data!!.results).isEmpty()
    }

    @Test
    fun `test searchItems with malformed response returns Error result`() = runBlocking {
        //given
        enqueueMockResponse("search_items_malformed.json")
        //when
        val result = repository.searchItems(
            siteId = "MCO",
            itemsPerPage = 10,
            pagingOffset = 0,
            category = null,
            query = "xbox"
        )
        //then
        assertThat(result is Result.Error).isTrue()
        assertThat(result.message is UiText.DynamicString).isTrue()
        assertThat(result.data).isNull()
    }
}
