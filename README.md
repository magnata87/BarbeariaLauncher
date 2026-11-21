# Barbearia Junior Torres Launcher

Launcher Android para TV em modo paisagem. Exibe relógio/data, QR Code do Wi-Fi e atalhos para YouTube, Spotify, UNITV Free, Velo Play, Xplus VOD e Xplus Express. Os atalhos localizam apps instalados pelo nome ou pacote.

## Requisitos
- Android Studio (versão atual)
- Android SDK Platform 35
- JDK 17 ou superior (Android Studio fornece JDK compatível)

## Gerar APK
1. Extraia este ZIP.
2. Abra a pasta `BarbeariaLauncher` no Android Studio (**Open**).
3. Aguarde o Gradle Sync e aceite instalar os componentes SDK solicitados, se aparecer.
4. Selecione **Build > Build APK(s)** (ou **Build > Build Bundle(s) / APK(s) > Build APK(s)**, conforme a versão).
5. O APK de debug será gerado em `app/build/outputs/apk/debug/app-debug.apk`.

## Instalar via ADB
`adb install -r app/build/outputs/apk/debug/app-debug.apk`

Para escolher como tela inicial: pressione Home, escolha **Barbearia Junior Torres** e selecione **Sempre**, caso a TV ofereça essa opção.

## QR Wi-Fi
O QR é codificado pela constante `WIFI_QR` em `app/src/main/java/br/com/juniortorres/launcher/MainActivity.java`. Se a rede ou senha mudar, edite essa constante.
