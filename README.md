## Cómo ejecutar el proyecto
El proyecto contiene dos implementaciones en la capa de aplicación (mock y queen)
**La versión final y funcional es queen**

Para iniciar la aplicación, ejecute la clase Main ubicada en:
`src/main/java/software/ulpgc/moneycalculator/application/queen/Main.java`

Cambios realizados respecto a la versión inicial dada por el profesor:
* Patrón Proxy: Implementado en CachedExchangeRateLoader para evitar llamadas redundantes a la API y mejorar el rendimiento
* Uso de Hilos (Threads) para la carga de imágenes en segundo plano sin congelar la UI
* Sistema de Caché de Imágenes para las banderas
* Renderizado personalizado de componentes (SwingCurrencyRenderer)
* UX/UI: Mejoras en el diseño visual (Padding, Título, Layout) y corrección de excepciones en banderas

Es necesaria la conexión a internet al ejecutar para la descarga de datos como las banderas pertenecientes a las divisas

Soy consciente de la subida de archivos no relevantes al directorio de github como por ejemplo la carpeta .idea pero he decidido mantenerlos en esta entrega para asegurar la estabilidad del proyecto e historial de versiones

Muchas gracias
