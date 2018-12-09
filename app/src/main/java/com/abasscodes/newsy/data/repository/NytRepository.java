package com.abasscodes.newsy.data.repository;

import android.content.Context;
import android.util.Log;
import com.abasscodes.newsy.data.rest.RetrofitClient;
import com.abasscodes.newsy.models.NytResponse;
import com.abasscodes.newsy.utils.NetworkHelper;
import com.abasscodes.newsy.utils.rx.RxUtil;
import com.abasscodes.newsy.viewmodels.NytArticlesViewModel;
import java.util.List;

public class NytRepository extends DataRepository {

  private static final String TAG = NytRepository.class.getSimpleName();

  public NytRepository(Context context) {
    super(context);
  }

  public void getResults(String section, NytArticlesViewModel viewModel) {
    if (NetworkHelper.getInstance()
      .isConnected(context)) {
      RetrofitClient.getNytApi()
        .getNytFeed(section)
        .compose(RxUtil.subscribeOnIoObserveOnUi())
        .subscribe(response -> {
          if (response.getResults() != null) {
            List<NytResponse.Result> bodyResults = response.getResults();
            if (bodyResults != null) {
              //                            cache.put(section, bodyResults);
              for (NytResponse.Result result : bodyResults) {
                //                                        nytDao.insert(result);
              }
              viewModel.setResults(bodyResults);
            }
          }
        }, throwable -> Log.d(TAG, "Network error " + throwable.getMessage()));
    } else {
      //viewModel.setResults(nytDao.getAll(section));
    }
  }
}
