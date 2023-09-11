terraform {
  required_providers {
    yandex = {
      source = "yandex-cloud/yandex"

    }
  }
  required_version = ">= 0.13"
}

provider "yandex" {
  zone = "ru-central1-a"
  folder_id = var.folder_id
  cloud_id = var.cloud_id
  service_account_key_file = file("~/key.json")
}