resource "aws_s3_bucket" "demo_bucket" {
 bucket = var.bucket_name


 tags = {
   Name        = var.bucket_name
   Environment = "Demo"
   Owner       = "sk"

 }
}


# Enable versioning (optional, production-style)
resource "aws_s3_bucket_versioning" "demo_bucket_versioning" {
 bucket = aws_s3_bucket.demo_bucket.id
 versioning_configuration {
   status = "Enabled"
 }
}

