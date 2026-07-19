# Test PR<<main to staging>> <<Update 19-07-2026: add GHCR>>
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
## Pruebas ->>Se agregan los siguientes parametros, esta prueba es con el fin de verificar los relase:
# 1. Descargamos el artefacto que compilamos al inicio
      - name: Download Artifact 📥
        uses: actions/download-artifact@v4
        with:
          name: java-app-package

      # 2. Lógica de incremento de Tag (la que ya tienes)
      - name: Generate Tag 🏷️
        id: tag_logic
        run: |
          LATEST_TAG=$(git describe --tags --abbrev=0 2>/dev/null || echo "v1.0.0")
          VERSION_PARTS=(${LATEST_TAG//./ })
          PATCH=$((VERSION_PARTS[2] + 1))
          NEW_TAG="${VERSION_PARTS[0]}.${VERSION_PARTS[1]}.$PATCH"
          echo "new_tag=$NEW_TAG" >> $GITHUB_OUTPUT
          git tag $NEW_TAG
          git push origin $NEW_TAG

      # 3. NUEVO: Crear la Release de GitHub y adjuntar el .jar
      - name: Create GitHub Release 🚀
        uses: softprops/action-gh-release@v2
        with:
          tag_name: ${{ steps.tag_logic.outputs.new_tag }}
          name: Release ${{ steps.tag_logic.outputs.new_tag }}
          files: "*.jar" # Sube todos los archivos .jar encontrados
          draft: false
          prerelease: false
      #  Paso 2. Lógica de incremento de Tag (Actualización del paso anterior)
#   Generate Tag 🏷️ (anterior)
       - name: Generate Tag 🏷️
         id: tag_logic
         run: |
             LATEST_TAG=$(git describe --tags --abbrev=0 2>/dev/null || echo "v1.0.0")
             VERSION_PARTS=(${LATEST_TAG//./ })
             PATCH=$((VERSION_PARTS[2] + 1))
             NEW_TAG="${VERSION_PARTS[0]}.${VERSION_PARTS[1]}.$PATCH"
             echo "new_tag=$NEW_TAG" >> $GITHUB_OUTPUT
             git tag $NEW_TAG
             git push origin $NEW_TAG