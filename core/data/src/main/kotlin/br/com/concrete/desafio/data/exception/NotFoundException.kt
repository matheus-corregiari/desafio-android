package br.com.concrete.desafio.data.exception

class NotFoundException internal constructor(message: String, code: Int, requestedPath: String) : SdkException(message, code, requestedPath)