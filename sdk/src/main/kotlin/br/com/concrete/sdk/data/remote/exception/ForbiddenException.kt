package br.com.concrete.sdk.data.remote.exception

class ForbiddenException internal constructor(message: String, code: Int, requestedPath: String) : SdkException(message, code, requestedPath)