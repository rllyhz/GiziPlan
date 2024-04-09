package id.rllyhz.giziplan.utils.fakes

import id.rllyhz.giziplan.data.local.LocalDataSource

class FakeLocalDataSource(
    giziDao: FakeGiziDao
) : LocalDataSource(giziDao)