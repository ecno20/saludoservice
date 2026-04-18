# Test PR<<main to staging>>
## Este archivo se actualizará próximamente con la documentación necesaria
// Test de flujo QA automático
# Prueba de flujo desde Develop a QA
## Objetivo: Hacer un PR desde develop hacía stagint para confirmar Action
## Este Action debe pasar directo a "despliegue" sin necesidad de hacer validación
## Desde develop a QA (staging)
# TEST >> actualización Repo DevOps Library
## En en el primer job:


build-and-scan:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: Set up JDK ☕
        uses: actions/setup-java@v4
        with:
          java-version: ${{ inputs.java-version }}
          distribution: 'temurin'
          cache: 'maven'
      - name: Build with Maven 🏗️
        run: mvn clean package -DskipTests

      # NUEVO PASO: Guardar el archivo .jar generado
      - name: Upload Build Artifact 📤
        uses: actions/upload-artifact@v4
        with:
          name: java-app-package
          path: target/*.jar  # Maven guarda el resultado aquí
          retention-days: 1   # Solo lo necesitamos para el flujo actual
## Update: Pruebas unitarias

Se agrega el bloque:

build-and-scan:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: Set up JDK ☕
        uses: actions/setup-java@v4
        with:
          java-version: ${{ inputs.java-version }}
          distribution: 'temurin'
          cache: 'maven'

      # NUEVO: Ejecución de pruebas unitarias <<< se agrega en repo central DEVOPS_Lib
      - name: Run Unit Tests 📋
        run: mvn test

      - name: Build with Maven 🏗️
        run: mvn clean package -DskipTests

      - name: Upload Build Artifact 📤
        uses: actions/upload-artifact@v4
        with:
          name: java-app-package
          path: target/*.jar
          retention-days: 1

## Esto con el fin de garantizar la calidad y robustecer nuestro pipeline  usando "Control de Calidad Automático (Quality Gates)"

## Y los siguientes steeps tanto en QA y PROD
## Para conservar el archivo .jar y se pueda compartir en todos los despliegues
steps:
      - name: Download Build Artifact 📥
        uses: actions/download-artifact@v4
        with:
          name: java-app-package
          
      - name: Verify Artifact 🔍
        run: ls -R  # Verificamos que el .jar esté presente
        
      # ... (aquí siguen tus pasos de echo actuales)


## Este paso Crear la Release de GitHub y adjuntar el .jar, se agrega en Repo Central, para efectuar
## Pruebas
      - name: Create GitHub Release 🚀
        uses: softprops/action-gh-release@v2
        with:
          tag_name: ${{ steps.tag_logic.outputs.new_tag }}
          name: Release ${{ steps.tag_logic.outputs.new_tag }}
          files: "*.jar" # Sube todos los archivos .jar encontrados
          draft: false
          prerelease: false