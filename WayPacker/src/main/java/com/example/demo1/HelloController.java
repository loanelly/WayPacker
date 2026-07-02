package com.example.demo1;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.collections.FXCollections;
import java.util.LinkedHashSet;
import java.util.Set;

public class HelloController {

    @FXML private ComboBox<String> destinationCombo;
    @FXML private ComboBox<String> landscapeCombo;
    @FXML private ComboBox<String> accommodationCombo;
    @FXML private ComboBox<String> seasonCombo;
    @FXML private ComboBox<String> climateCombo;
    @FXML private ComboBox<String> durationCombo;

    @FXML private CheckBox medicationCheck;
    @FXML private CheckBox disabilityCheck;

    @FXML private TextArea resultArea;

    @FXML
    public void initialize() {
        // Подтягивает массив стран из расширенной базы данных
        destinationCombo.setItems(FXCollections.observableArrayList(TravelDatabase.getDestinations()));
    }
    @FXML
    protected void onGenerateClick() {
        String dest = destinationCombo.getValue();
        String landscape = landscapeCombo.getValue();
        String accom = accommodationCombo.getValue();
        String season = seasonCombo.getValue();
        String climate = climateCombo.getValue();
        String duration = durationCombo.getValue();

        if (dest == null || landscape == null || accom == null || season == null || climate == null || duration == null) {
            resultArea.setText("⚠️ Ошибка: Пожалуйста, заполните абсолютно ВСЕ параметры для генерации полного списка!");
            return;
        }

        Set<String> finalItems = new LinkedHashSet<>();

        // Базовые универсальные вещи (Всегда с собой)
        finalItems.add("Загранпаспорт / Национальный паспорт / ID-карта (+ бумажные и цифровые копии)");
        finalItems.add("Билеты на все виды транспорта, маршрутные квитанции и бронирования");
        finalItems.add("Две банковские карты разных платежных систем + наличная валюта мелкого номинала");
        finalItems.add("Смартфон, защитное стекло, оригинальный кабель питания и надежный Пауэрбанк");
        finalItems.add("Личные гигиенические средства (зубная щетка, паста, дезодорант, влажные салфетки)");
        finalItems.add("Мини-аптечка первой помощи (сорбенты, пластыри, жаропонижающее, антисептик)");

        // Расчет под длительность поездки
        switch (duration) {
            case "Короткая (1-3 дня)" -> {
                finalItems.add("Сменное белье и носки (2-3 комплекта)");
                finalItems.add("Один универсальный запасной комплект одежды");
            }
            case "Средняя (4-10 дней)" -> {
                finalItems.add("Сменное белье и носки (5-7 комплектов)");
                finalItems.add("2-3 варианты верха (футболки/рубашки) и 1 удобный запасной низ");
                finalItems.add("Дорожный мини-набор косметики (шампунь, гель для душа, мочалка)");
            }
            case "Долгий отпуск (от 11 дней)" -> {
                finalItems.add("Сменное белье и носки (8-10 комплектов) + концентрированное средство для экспресс-стирки");
                finalItems.add("Разнообразная одежда на 5-6 дней (с возможностью легкого комбинирования)");
                finalItems.add("Маленькие маникюрные ножницы или пилочка для ногтей");
                finalItems.add("Нитки и иголка в мини-футляре");
            }
            default -> finalItems.add("Сменное белье и базовая одежда, рассчитанные индивидуально под ваши задачи");
        }
        // Загрузка списков из TravelDatabase
        finalItems.addAll(TravelDatabase.getItemsForDestination(dest));
        finalItems.addAll(TravelDatabase.getItemsForLandscape(landscape));
        finalItems.addAll(TravelDatabase.getItemsForAccommodation(accom));
        finalItems.addAll(TravelDatabase.getItemsForClimate(climate));
        finalItems.addAll(TravelDatabase.getItemsForSeason(season));

        // Обработка инклюзивных флажков
        if (medicationCheck.isSelected()) {
            finalItems.add("❗️ ЖИЗНЕННО ВАЖНО: Выяснить, разрешены ли ваши препараты для перевозки и ввоза таможней в: " + dest);
            finalItems.add("❗️ Взять заверенный медицинский рецепт от врача на английском языке (с международными названиями веществ — МНН)");
            finalItems.add("Полноценный запас специфических медикаментов (ингаляторы, тест-полоски, антигистаминные) с избытком на 5 дней");
            finalItems.add("Положить все жизненно важные лекарства строго в РУЧНУЮ КЛАДЬ (не сдавать в багаж ни в коем случае)");
        }

        if (disabilityCheck.isSelected()) {
            finalItems.add("♿️ Направить официальный запрос авиаперевозчику за 48 часов для подтверждения сопровождения (PRM Assistance)");
            finalItems.add("♿️ Связаться с локацией проживания (" + accom + ") для верификации безбарьерной среды (высота порогов, лифты)");
            finalItems.add("Технический паспорт на кресло-коляску / спецификация аккумулятора (для электрических моделей)");
            finalItems.add("Оригинал справки / удостоверения об инвалидности + нотариальный перевод на английский язык");
        }

        // Вывод на экран
        StringBuilder sb = new StringBuilder();
        sb.append("==================================================\n");
        sb.append("        🎒 WAYPACKER MAXIMUM TRIP CHECKLIST      \n");
        sb.append("==================================================\n");
        sb.append("📍 Направление: ").append(dest).append("\n");
        sb.append("🏞 Ландшафт:    ").append(landscape).append("\n");
        sb.append("🏠 Проживание:  ").append(accom).append("\n");
        sb.append("⏳ Срок поездки:").append(duration).append("\n");
        sb.append("🍂 Сезон/Климат:").append(season).append(" / ").append(climate).append("\n");
        sb.append("==================================================\n\n");

        int counter = 1;
        for (String item : finalItems) {
            sb.append(String.format("[%02d] [ ] %s\n", counter++, item));
        }

        resultArea.setText(sb.toString());
    }
    // Метод для копирования чек-листа в буфер обмена компьютера
    @FXML
    protected void onCopyClick() {
        String textToCopy = resultArea.getText();

        // Проверяем, что чек-лист уже сгенерирован и поле не пустое
        if (textToCopy == null || textToCopy.trim().isEmpty() || textToCopy.startsWith("⚠️")) {
            return;
        }

        // Создаем системный буфер обмена JavaFX
        javafx.scene.input.Clipboard clipboard = javafx.scene.input.Clipboard.getSystemClipboard();
        javafx.scene.input.ClipboardContent content = new javafx.scene.input.ClipboardContent();

        // Помещаем текст в буфер
        content.putString(textToCopy);
        clipboard.setContent(content);

        // Небольшой визуальный фидбек для пользователя в самом окне вывода
        String originalText = resultArea.getText();
        resultArea.setText("✅ ЧЕК-ЛИСТ УСПЕШНО СКОПИРОВАН В БУФЕР ОБМЕНА!\n\n" + originalText);
    }
} // Конец класса HelloController
