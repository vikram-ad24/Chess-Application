// polyfills.js
import { Buffer } from "buffer";

if (typeof global === "undefined") {
  window.global = window; // or globalThis.global = globalThis
}

if (typeof globalThis.Buffer === "undefined") {
  globalThis.Buffer = Buffer;
}

if (typeof globalThis.crypto === "undefined") {
  console.warn("crypto not available");
}
