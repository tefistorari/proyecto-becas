package com.UTN_BECAS.Sistema_Becas.Archivos.DTO;

   import org.springframework.core.io.Resource;

public record ArchivoDescarga(Resource recurso, String nombreOriginal, String contentType) {}
