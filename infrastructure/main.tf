provider "aws" {
  region = "eu-north-1" # Update this if your AWS region is different
}

resource "aws_security_group" "devops_sg" {
  name        = "devops_project_sg"
  description = "Allow SSH, Jenkins, App, and Monitoring ports"

  # SSH
  ingress { from_port = 22, to_port = 22, protocol = "tcp", cidr_blocks = ["0.0.0.0/0"] }
  # Jenkins
  ingress { from_port = 8080, to_port = 8080, protocol = "tcp", cidr_blocks = ["0.0.0.0/0"] }
  # Event App (Allen moved this to 8081!)
  ingress { from_port = 8081, to_port = 8081, protocol = "tcp", cidr_blocks = ["0.0.0.0/0"] }
  # Prometheus (For Namita)
  ingress { from_port = 9090, to_port = 9090, protocol = "tcp", cidr_blocks = ["0.0.0.0/0"] }
  # Grafana (For Namita)
  ingress { from_port = 3000, to_port = 3000, protocol = "tcp", cidr_blocks = ["0.0.0.0/0"] }

  egress { from_port = 0, to_port = 0, protocol = "-1", cidr_blocks = ["0.0.0.0/0"] }
}

resource "aws_instance" "devops_server" {
  ami             = "ami-09a9858f49fc25292" # Ubuntu 22.04 AMI (Verify for your region)
  instance_type   = "t3.micro"
  security_groups = [aws_security_group.devops_sg.name]
  key_name        = "your-key-name" # Replace with the actual name of your .pem key in AWS

  tags = {
    Name = "DevOps-Automated-Server"
  }
}
