## Overview

The Simple asynchronous translation application:

1. **Translate Text**: Translate text.
2. **Get Translation Logs**: Retrieve logs of all translation requests with user IP.
3. **Swagger UI**: Swagger docs
4. **Main page**: Simple UI for API

## Endpoints

### Translate Text

- **URL**: `/translate`
- **Method**: `POST`
- **Description**: Translate the provided text according to the specified translation method.

#### Request Parameters

- **Body**: `TranslationRequestDTO`
    - `text` (String): The text to be translated.
    - `source` (String): The source language for the translation.
    - `target` (String): The target language for the translation.

- **Query Parameters**
    - `correctTranslation` (Boolean, optional, default: `true`):
        - `true` for chunk-based translation.
        - `false` for word-based translation.

#### Example Request

```http
POST /translate?correctTranslation=true
Content-Type: application/json

{
  "text": "Hello, world!",
  "source": "en"
  "target": "ru"
}
```

### UI Page
- **URL**: `/mainpage`

### Get Logs
- **URL**: `/logs`
- **Method**: `GET`
- **Description**: Get translation logs with text and user IP

