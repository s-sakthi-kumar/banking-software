variable "bucket_name" {
 description = "Name of the sk's S3 bucket"
 type        = string
 default     = "sk-demo-bucket-20260831"
}

variable "aws_access_key" {
  description = "AWS Access Key ID"
  type        = string
  sensitive   = true
}

variable "aws_secret_key" {
  description = "AWS Secret Access Key"
  type        = string
  sensitive   = true
}

