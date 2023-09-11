resource "yandex_compute_instance" "vm" {
  count = 1
  name = "terraform1"

  boot_disk {
    initialize_params {
      name = "disk1"
      image_id = "fd8ba0ukgkn46r0qr1gi"
    }
  }

  resources {
    cores = 2
    memory = 2
    core_fraction = 5
  }

  network_interface {
    subnet_id = data.yandex_vpc_subnet.subnet-1.id
    nat = true
  }

  metadata = {
    ssh-keys = "ubuntu:${file("~/.ssh/id_ed25519.pub")}"
  }

  labels = {
    task_name = var.task_name
    user_email = var.email
  }
}

data "yandex_vpc_subnet" "subnet-1" {
  name = "default-ru-central1-a"
}

resource "local_file" "inventory" {
  filename = "inventory.ini"
  content = templatefile("templates/vm.tmpl", {
    vm = yandex_compute_instance.vm[*].network_interface.0.nat_ip_address
  })
}

