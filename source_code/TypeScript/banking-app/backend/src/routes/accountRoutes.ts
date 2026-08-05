import { Router } from "express";

const router = Router();

router.get("/", (_req, res) => {
  res.json({
    message: "Accounts route works",
  });
});

router.get("/:id", (req, res) => {
  const { id } = req.params;

  res.json({
    accountId: id,
  });
});

router.post("/", (req, res) => {
  const account = req.body;

  res.status(201).json({
    message: "Account created",
    account,
  });
});

export default router;
