package com.qatros.qtn_bina_murid.data

import com.qatros.qtn_bina_murid.data.local.LocalRepository
import com.qatros.qtn_bina_murid.data.remote.RemoteRepository

class AppRepository(private val localRepository: LocalRepository, private val remoteRepository: RemoteRepository) {
}