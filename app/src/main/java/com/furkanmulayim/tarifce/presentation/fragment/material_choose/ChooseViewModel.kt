package com.furkanmulayim.tarifce.presentation.fragment.material_choose

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.furkanmulayim.tarifce.data.model.CategoryData
import com.furkanmulayim.tarifce.data.model.Category
import com.furkanmulayim.tarifce.data.model.Material
import com.furkanmulayim.tarifce.data.service.materials.MaterialsAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChooseViewModel : ViewModel() {

    val categoriesLiveData = MutableLiveData<List<Category>>()
    private val materialAPIService = MaterialsAPIService()
    private val disposable = CompositeDisposable()
    val allCategories = mutableListOf<Category>()

    fun getData(){
        getFoodFromApi()
    }

    //İlerde repository katmanına alınacak!
    // download data from API
    private fun getFoodFromApi() {
        disposable.add(
            materialAPIService.getMaterials()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<CategoryData>>() {
                    override fun onSuccess(categoryDataList: List<CategoryData>) {
                        categoryDataList.forEach { categoryData ->
                            categoryData.categories.forEach { category ->
                                val materials = category.materials.map {
                                    Material(it.name, it.imageUrl)
                                }
                                allCategories.add(Category(category.name,
                                    materials
                                ))
                            }
                        }
                        categoriesLiveData.value = allCategories
                    }
                    override fun onError(e: Throwable) {
                    }
                })
        )
    }


}